package com.acquaexchange.base

import androidx.databinding.ViewDataBinding

abstract class BaseViewHolder<Binding : ViewDataBinding, Item>(val binding: Binding) :
    BaseHolder<Item>(binding.root)
